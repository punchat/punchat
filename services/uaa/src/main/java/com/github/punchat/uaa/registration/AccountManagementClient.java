package com.github.punchat.uaa.registration;

import com.github.punchat.dto.am.WorkspaceAccessCodeValidation;
import com.github.punchat.dto.am.WorkspaceAccessCodeValidationResult;
import com.github.punchat.dto.am.WorkspaceEmailValidation;
import com.github.punchat.dto.am.WorkspaceEmailValidationResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("account-management")
public interface AccountManagementClient {
    @PutMapping("/workspace/members/check")
    WorkspaceEmailValidationResult checkEmail(WorkspaceEmailValidation validation);

    @PutMapping("/workspace/codes/check")
    WorkspaceAccessCodeValidationResult checkAccessCode(WorkspaceAccessCodeValidation validation);
}
