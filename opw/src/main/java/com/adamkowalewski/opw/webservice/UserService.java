/*
 * The MIT License
 *
 * Copyright 2015 Adam Kowalewski.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.adamkowalewski.opw.webservice;

import com.adamkowalewski.opw.webservice.controller.UserServiceEjb;
import com.adamkowalewski.opw.webservice.dto.UserRegisterDto;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents user perspective.
 *
 * @author Adam Kowalewski
 */
@Path("/user")
public class UserService extends AbstractService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @EJB
    UserServiceEjb userServiceEjb;

    public UserService() {
    }

    @GET
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response login(
            @NotNull @HeaderParam(OPW_HEADER_LOGIN) String login,
            @NotNull @HeaderParam(OPW_HEADER_PASSWORD) String password,
            @HeaderParam(OPW_HEADER_DEBUG_ERROR500) String debug) {

        if (debug != null) {
            logger.info("Debug call for login()");
            return mockServerError();
        }

        return userServiceEjb.login(login, password);
    }

    @GET
    @Path("/{userId}/obwodowa")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response loadObwodowaShortList(
            @NotNull @PathParam("userId") int userId,
            @NotNull @HeaderParam(OPW_HEADER_LOGIN) String login,
            @NotNull @HeaderParam(OPW_HEADER_TOKEN) String token,
            @HeaderParam(OPW_HEADER_DEBUG_ERROR500) String debug) {

        if (debug != null) {
            return mockServerError();
        }

        return userServiceEjb.loadObwodowaShortList(userId, login, token);
    }

    @GET
    @Path("/logout")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response logout(
            @NotNull @HeaderParam(OPW_HEADER_LOGIN) String login,
            @NotNull @HeaderParam(OPW_HEADER_TOKEN) String token,
            @HeaderParam(OPW_HEADER_DEBUG_ERROR500) String debug) {

        if (debug != null) {
            return mockServerError();
        }

        return userServiceEjb.logout(login, token);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(
            @NotNull @HeaderParam(OPW_HEADER_API_TOKEN) String apiToken,
            @NotNull UserRegisterDto newUser) {

        /**
         * WiP
         */
        return userServiceEjb.register();

    }

}
