package com.github.punchat.am.domain.invite;

import com.github.punchat.am.domain.access.AccessCodeService;
import com.github.punchat.am.events.EventBus;
import com.github.punchat.am.id.IdService;
import com.github.punchat.dto.am.access.AccessCodeValidationResult;
import com.github.punchat.dto.am.access.NewAccessCodeRequest;
import com.github.punchat.dto.am.access.WorkspaceAccessCodeValidation;
import com.github.punchat.dto.am.access.WorkspaceAccessCodeValidationResult;
import com.github.punchat.dto.am.invite.EmailValidationResult;
import com.github.punchat.dto.am.invite.WorkspaceEmailValidation;
import com.github.punchat.dto.am.invite.WorkspaceEmailValidationResult;
import com.github.punchat.dto.am.invite.WorkspaceInvitation;
import com.github.punchat.events.AccessCodeGeneratedEvent;
import com.github.punchat.events.InviteToWorkspaceEvent;
import com.github.punchat.log.Trace;
import com.github.punchat.starter.uaa.client.context.AuthContext;
import org.springframework.stereotype.Service;

@Trace
@Service
public class WorkspaceInviteServiceImpl implements WorkspaceInviteService {
    private final WorkspaceInviteRepository workspaceInviteRepository;
    private final AccessCodeService accessCodeService;
    private final EventBus eventBus;
    private final AuthContext authContext;
    private final IdService idService;

    public WorkspaceInviteServiceImpl(WorkspaceInviteRepository workspaceInviteRepository, AccessCodeService accessCodeService, EventBus eventBus, AuthContext authContext, IdService idService) {
        this.workspaceInviteRepository = workspaceInviteRepository;
        this.accessCodeService = accessCodeService;
        this.eventBus = eventBus;
        this.authContext = authContext;
        this.idService = idService;
    }

    public WorkspaceInvite getInvite(String email) {
        return workspaceInviteRepository.findByEmail(email);
    }

    @Override
    public void createWorkspaceInvite(WorkspaceInvitation invitation) {
        String email = invitation.getEmail();
        WorkspaceInvite workspaceInvite = new WorkspaceInvite();
        if (workspaceInviteRepository.existsByEmail(email)) {
            workspaceInvite = workspaceInviteRepository.findByEmail(email);
        } else {
            Long senderUserId = authContext.get().getUserInfo().get().getUserId();
            workspaceInvite.setId(idService.next());
            workspaceInvite.setSenderUserId(senderUserId);
            workspaceInvite.setEmail(email);
            workspaceInviteRepository.save(workspaceInvite);
        }
        eventBus.publish(new InviteToWorkspaceEvent(
                workspaceInvite.getSenderUserId(), invitation.getEmail()));
    }

    @Override
    public WorkspaceEmailValidationResult checkWorkspaceInvite(WorkspaceEmailValidation emailValidation) {
        String email = emailValidation.getEmail();
        if (workspaceInviteRepository.existsByEmail(email)) {
            WorkspaceInvite workspaceInvite = getInvite(email);
            workspaceInviteRepository.save(workspaceInvite);
            return new WorkspaceEmailValidationResult(email, EmailValidationResult.VALID);
        }
        return new WorkspaceEmailValidationResult(email, EmailValidationResult.INVALID);
    }

    @Override
    public void requestAccessCode(NewAccessCodeRequest accessCodeRequest) {
        String email = accessCodeRequest.getEmail();
        if (!workspaceInviteRepository.existsByEmail(email)) {
            throw new WorkspaceInviteWasNotFound(email);
        }
        WorkspaceInvite workspaceInvite = getInvite(email);
        if (workspaceInvite.getAccessCode() != null) {
            workspaceInvite.setAccessCode(
                    accessCodeService.refreshAccessCode(workspaceInvite.getAccessCode()));
        }
        workspaceInvite.setAccessCode(accessCodeService.generateAccessCode());
        eventBus.publish(new AccessCodeGeneratedEvent(
                workspaceInvite.getEmail(),
                workspaceInvite.getAccessCode().getCode()));
        workspaceInviteRepository.save(workspaceInvite);
    }

    @Override
    public WorkspaceAccessCodeValidationResult checkAccessCode(WorkspaceAccessCodeValidation accessCodeValidation) {
        WorkspaceInvite email = getInvite(accessCodeValidation.getEmail());
        AccessCodeValidationResult result =
                accessCodeService.checkAccessCode(email.getAccessCode(), accessCodeValidation);
        return new WorkspaceAccessCodeValidationResult(accessCodeValidation.getEmail(),
                accessCodeValidation.getCode(), result);
    }

//    @Override
//    public void checkRegistrationData(WorkspaceRegistration registrationDto) {
//        WorkspaceInvite email
//    }
}
