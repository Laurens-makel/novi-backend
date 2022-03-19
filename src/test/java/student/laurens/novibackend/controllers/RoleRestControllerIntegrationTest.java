package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.repositories.RoleRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class to test allowed actions on {@link RoleRestController}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class RoleRestControllerIntegrationTest extends ControllerIntegrationTestBase<Role>  {

    @Override
    protected String getUrlForGet() {
        return "/roles";
    }

    @Override
    protected String getUrlForGet(Role resource) {
        return null;
    }

    @Override
    protected String getUrlForPut(Role resource) {
        Integer id = resource.getId();
        return "/roles/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/roles";
    }

    @Override
    protected String getUrlForDelete(Role resource) {
        Integer id = resource.getId();
        return "/roles/" + (id == null ? 9999 : id);
    }

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
        getAsJson()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getRoles_AsUser_JSON_Ok() throws Exception {
        // when
        ResultActions mvc = getAsJson()

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getRoles_AsUser_XML_Ok() throws Exception {
        // when
        ResultActions mvc = getAsXml()

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getRoles_AsAdmin_JSON_Ok() throws Exception {
        // when
        ResultActions mvc = getAsJson()

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getRoles_AsAdmin_XML_Ok() throws Exception {
        // when
        ResultActions mvc = getAsXml()

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getRoles_AsContentCreator_JSON_Ok() throws Exception {
        // when
        ResultActions mvc = getAsJson()

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getRoles_AsContentCreator_XML_Ok() throws Exception {
        // when
        ResultActions mvc = getAsXml()

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getRoles_AsModerator_JSON_Ok() throws Exception {
        // when
        ResultActions mvc = getAsJson()

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getRoles_AsModerator_XML_Ok() throws Exception {
        // when
        ResultActions mvc = getAsXml()

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    public void postRoles_isUnauthorized() throws Exception {
        // when
        postAsJson(createRole("TEST"))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postRoles_AsUser_Forbidden() throws Exception {
        // when
        postAsJson(createRole("TEST"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postRoles_AsContentCreator_Forbidden() throws Exception {
        // when
        postAsJson(createRole("TEST"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postRoles_AsModerator_Forbidden() throws Exception {
        // when
        postAsJson(createRole("TEST"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postRoles_AsAdmin_JSON_Ok() throws Exception {
        // when
        ResultActions mvc = postAsJson(createRole("TEST"))

        // then
        .andExpect(status().isCreated());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postRoles_AsAdmin_XML_Ok() throws Exception {
        // when
        ResultActions mvc = postAsXml(createRole("TEST2"))

        // then
        .andExpect(status().isCreated());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteRoles_AsAdmin_JSON_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        ResultActions mvc = deleteAsJson(role)

        // then
        .andExpect(status().isAccepted());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteRoles_AsAdmin_XML_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        ResultActions mvc = deleteAsXml(role)

        // then
        .andExpect(status().isAccepted());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteRoles_AsUser_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteRoles_AsContentCreator_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteRoles_AsModerator_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void deleteRoles_isUnauthorized() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        // when
        deleteAsJson(role)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingRole_AsAdmin_AcceptJSON_NotFound() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = deleteAsJson(role)

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingRole_AsAdmin_AcceptXML_NotFound() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = deleteAsXml(role)

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectXmlResponse(mvc);
    }


    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteNonExistingRole_AsUser_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = deleteAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteNonExistingRole_AsContentCreator_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = deleteAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteNonExistingRole_AsModerator_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = deleteAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void deleteNonExistingRole_isUnauthorized() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = deleteAsJson(role)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateRoles_AsAdmin_JSON_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        ResultActions mvc = updateAsJson(role)

        // then
        .andExpect(status().isAccepted());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateRoles_AsAdmin_XML_Ok() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE2");

        // when
        ResultActions mvc = updateAsXml(role)

        // then
        .andExpect(status().isAccepted());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateRoles_AsUser_Forbidden() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        updateAsJson(role)

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
        updateAsJson(role)

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
        updateAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }


    @Test
    public void updateRoles_isUnauthorized() throws Exception {
        // given
        Role role = saveRole(createRole("TEST_ROLE"));

        role.setName("UPDATED_ROLE");

        // when
        updateAsJson(role)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateNonExistingRole_AsAdmin_AcceptJson_NotFound() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = updateAsJson(role)

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateNonExistingRole_AsAdmin_AcceptXML_NotFound() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        ResultActions mvc = updateAsXml(role)

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectXmlResponse(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateNonExistingRole_AsUser_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateNonExistingRole_AsContentCreator_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateNonExistingRole_AsModerator_Forbidden() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateAsJson(role)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void updateNonExistingRole_isUnauthorized() throws Exception {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        updateAsJson(role)

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
