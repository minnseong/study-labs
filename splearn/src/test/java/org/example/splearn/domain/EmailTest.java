package org.example.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("minnseong@splearn.app");
        var email2 = new Email("minnseong@splearn.app");

        assertThat(email1).isEqualTo(email2);
    }

}