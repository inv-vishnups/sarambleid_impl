package com.inv.scrambleid.mapping;

import com.inv.scrambleid.entity.Email;
import com.inv.scrambleid.forms.EmailsForm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailMapper {

    public Email toEmail(EmailsForm emailsForms) {
        Email email = new Email();

        email.setValue(emailsForms.value());
        email.setType(emailsForms.type());
        email.setPrimary(emailsForms.primary());

        return email;
    }
}
