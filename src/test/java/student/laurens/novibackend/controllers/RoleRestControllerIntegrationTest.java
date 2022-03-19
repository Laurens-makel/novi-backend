package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.repositories.RoleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
        getRoles()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getRoles_AsUser_Ok() throws Exception {
        // when
        getRoles()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getRoles_AsAdmin_Ok() throws Exception {
        // when
        getRoles()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getRoles_AsContentCreator_Ok() throws Exception {
        // when
        getRoles()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getRoles_AsModerator_Ok() throws Exception {
        // when
        getRoles()

        // then
        .andExpect(status().isOk());
    }

    private ResultActions getRoles() throws Exception {
        return mvc.perform(get("/roles")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postRoles_isUnauthorized() throws Exception {
        // when
        postRole()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postRoles_AsUser_Forbidden() throws Exception {
        // when
        postRole()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postRoles_AsContentCreator_Forbidden() throws Exception {
        // when
        postRole()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postRoles_AsModerator_Forbidden() throws Exception {
        // when
        postRole()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postRoles_AsAdmin_Ok() throws Exception {
        // when
        postRole()

        // then
        .andExpect(status().isCreated());
    }

    private ResultActions postRole() throws Exception {
        return mvc.perform(post("/roles")
                .content(asJsonString(createRole("TEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteRoles_AsAdmin_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteRole(role)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteRoles_AsUser_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteRole(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteRoles_AsContentCreator_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteRole(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteRoles_AsModerator_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteRole(role)

        // then
        .andExpect(status().isForbidden());
    }
    @Test
    public void deleteRoles_isUnauthorized() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteRole(role)

        // then
        .andExpect(status().isUnauthorized());
    }

    private ResultActions deleteRole(Role role) throws Exception {
        return mvc.perform(delete("/roles/" + repository.getRoleByName(role.getName()).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingRole_AsAdmin_AcceptJSON_NotFound() throws Exception {
        // when
        deleteNonExistingRole()

        // then
        .andExpect(status().isNotFound())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andDo(print());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingRole_AsAdmin_AcceptXML_NotFound() throws Exception {
        // when
        deleteNonExistingRole(MediaType.APPLICATION_XML)

        // then
        .andExpect(status().isNotFound())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE))
        .andDo(print());
    }


    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteNonExistingRole_AsUser_Forbidden() throws Exception {
        // when
        deleteNonExistingRole()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteNonExistingRole_AsContentCreator_Forbidden() throws Exception {
        // when
        deleteNonExistingRole()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteNonExistingRole_AsModerator_Forbidden() throws Exception {
        // when
        deleteNonExistingRole()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void deleteNonExistingRole_isUnauthorized() throws Exception {
        // when
        deleteNonExistingRole()

        // then
        .andExpect(status().isUnauthorized());
    }


    private ResultActions deleteNonExistingRole() throws Exception {
        return deleteNonExistingRole(MediaType.APPLICATION_JSON);
    }

    private ResultActions deleteNonExistingRole(MediaType acceptMediaType) throws Exception {
        return mvc.perform(delete("/roles/9999")
                .accept(acceptMediaType));
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateRoles_AsAdmin_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        updateRole(role)

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
        updateRole(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateRoles_AsContentCreator_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        updateRole(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateRoles_AsModerator_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        updateRole(role)

        // then
        .andExpect(status().isForbidden());
    }


    @Test
    public void updateRoles_isUnauthorized() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        updateRole(role)

        // then
        .andExpect(status().isUnauthorized());
    }

    private ResultActions updateRole(Role role) throws Exception {
        return mvc.perform(put("/roles/" + role.getId())
                .content(asJsonString(role))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateNonExistingRole_AsAdmin_AcceptJson_NotFound() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateNonExistingRole(MediaType.APPLICATION_JSON, role)

        // then
        .andExpect(status().isNotFound())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andDo(print());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateNonExistingRole_AsAdmin_AcceptXML_NotFound() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateNonExistingRole(MediaType.APPLICATION_XML, role)

        // then
        .andExpect(status().isNotFound())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE))
        .andDo(print());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateNonExistingRole_AsUser_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateNonExistingRole(MediaType.APPLICATION_XML, role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateNonExistingRole_AsContentCreator_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateNonExistingRole(MediaType.APPLICATION_XML, role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateNonExistingRole_AsModerator_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateNonExistingRole(MediaType.APPLICATION_XML, role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void updateNonExistingRole_isUnauthorized() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateNonExistingRole(MediaType.APPLICATION_XML, role)

        // then
        .andExpect(status().isUnauthorized());
    }

    private ResultActions updateNonExistingRole(MediaType acceptMediaType, Role role) throws Exception {
        return mvc.perform(put("/roles/9999")
                .content(asJsonString(role))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(acceptMediaType));
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
