package com.inv.scrambleid.mapping;


import com.inv.scrambleid.entity.Name;
import com.inv.scrambleid.entity.User;
import com.inv.scrambleid.forms.NameForm;
import org.springframework.stereotype.Component;

@Component
public class NameMapper {

    public Name toEntity(NameForm form) {
        if (form == null) return null;

        Name name = new Name();
        name.setGivenName(form.givenName());
        name.setFamilyName(form.familyName());

        return name;
    }

    /**
     * Handles bidirectional mapping safely
     */
    public Name toEntity(NameForm form, User user) {
        if (form == null) return null;

        Name name = toEntity(form);
        name.setUser(user);   // 🔥 important
        return name;
    }
}