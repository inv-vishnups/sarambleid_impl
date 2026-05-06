package com.inv.scrambleid.forms;

import java.util.List;

public record UserForm(
        String userName,
        NameForm name,
        List<EmailsForm> emails,
        List<String> roles

) {
}

