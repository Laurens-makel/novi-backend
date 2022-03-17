package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.repositories.RoleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleRestControllerIntegrationTest extends ControllerIntegrationTestBase {

    @Autowired
    private RoleRepository repository;

    @After
    public void after(){
        Role role = repository.getRoleByName("TEST_ROLE");
        if(role != null){
            repository.delete(role);
        }
    }

    @Test
    public void getRoles_isUnauthorized() throws Exception {
        // when
        mvc.perform(get("/roles")
            .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getRoles_AsUser_Ok() throws Exception {
        // when
        mvc.perform(get("/roles")
            .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getRoles_AsAdmin_Ok() throws Exception {
        // when
        mvc.perform(get("/roles")
                .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    @Test
    public void postRoles_isUnauthorized() throws Exception {
        // when
        mvc.perform(post("/roles")
            .content(asJsonString(createRole("TEST")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postRoles_AsUser_Forbidden() throws Exception {
        // when
        mvc.perform(post("/roles")
            .content(asJsonString(createRole("TEST")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postRoles_AsAdmin_Ok() throws Exception {
        // when
        mvc.perform(post("/roles")
            .content(asJsonString(createRole("TEST")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteRoles_AsAdmin_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        mvc.perform(delete("/roles/" + repository.getRoleByName(role.getName()).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteRoles_AsUser_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        mvc.perform(delete("/roles/" + repository.getRoleByName(role.getName()).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void deleteRoles_isUnauthorized() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        mvc.perform(delete("/roles/" + repository.getRoleByName(role.getName()).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateRoles_AsAdmin_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        mvc.perform(put("/roles/" + role.getId())
            .content(asJsonString(role))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateRoles_AsUser_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        mvc.perform(put("/roles/" + role.getId())
            .content(asJsonString(role))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void updateRoles_isUnauthorized() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        mvc.perform(put("/roles/" + role.getId())
            .content(asJsonString(role))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isUnauthorized());
    }

    private Role createRole(String rolename){
        Role role = new Role();
        role.setName(rolename);

        return role;
    }

    private Role saveRole(Role role){
        repository.save(role);

        return role;
    }
}
