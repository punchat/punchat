package com.github.punchat.messaging.domain.channel;

import com.github.punchat.messaging.domain.role.RoleFinder;
import com.github.punchat.messaging.domain.role.RoleFinderImpl;
import com.github.punchat.messaging.security.AuthService;
import com.github.punchat.messaging.security.AuthServiceImpl;
import com.github.punchat.messaging.MockIdService;
import com.github.punchat.messaging.domain.member.MemberRepository;
import com.github.punchat.messaging.domain.member.MemberService;
import com.github.punchat.messaging.domain.member.MemberServiceImpl;
import com.github.punchat.messaging.domain.role.DefaultRoleAutoCreator;
import com.github.punchat.messaging.domain.role.RoleRepository;
import com.github.punchat.messaging.domain.user.*;
import com.github.punchat.messaging.id.IdService;
import com.github.punchat.starter.uaa.client.context.AuthContext;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JpaTestsConfig {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DirectChannelRepository directChannelRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BroadcastChannelRepository broadcastChannelRepository;
    @Autowired
    private RoleRepository roleRepository;

    @MockBean
    private AuthContext authContext;

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, directChannelRepository, idService());
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(authService(), memberRepository, roleFinder(), idService());
    }

    @Bean
    public RoleFinder roleFinder() {
        return new RoleFinderImpl(roleRepository);
    }

    @Bean
    public BroadcastChannelFinder broadcastChannelFinder() {
        return new BroadcastChannelFinderImpl(broadcastChannelRepository);
    }

    @Bean
    public ChannelService channelService() {
        return new ChannelServiceImpl(authService(), broadcastChannelRepository, idService(), channelMapper(), memberService(), userFinder(), broadcastChannelFinder());
    }

    @Bean
    public ChannelMapper channelMapper() {
        return Mappers.getMapper(ChannelMapper.class);
    }

    @Bean
    public UserFinder userFinder() {
        return new UserFinderImpl(userRepository);
    }

    @Bean
    public AuthService authService() {
        return new AuthServiceImpl(authContext, userRepository);
    }

    @Bean
    public IdService idService() {
        return new MockIdService();
    }

    @Bean
    public DefaultRoleAutoCreator defaultRoleAutoCreator() {
        return new DefaultRoleAutoCreator(roleRepository, idService());
    }
}
