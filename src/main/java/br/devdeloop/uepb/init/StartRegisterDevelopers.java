package br.devdeloop.uepb.init;

import br.devdeloop.uepb.dtos.register.RegisterRequest;
import br.devdeloop.uepb.services.user.UserManagementService;
import br.devdeloop.uepb.util.AppUserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRegisterDevelopers implements CommandLineRunner {

    @Autowired
    private UserManagementService userManagementService;

    @Override
    public void run(String... args) throws Exception {
        userManagementService.registerCredentials(new RegisterRequest("admin", "admin", AppUserRoleEnum.DEVELOPER));
    }
}
