package com.avoinovan.drawlot.api;

import com.avoinovan.drawlot.apiclient.youtube.YouTubeApiClient;
import com.avoinovan.drawlot.auth.service.YoutubeAuthService;
import com.avoinovan.drawlot.model.entity.User;
import com.avoinovan.drawlot.model.repository.AccessTokenRepository;
import com.avoinovan.drawlot.model.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author by avoinovan
 */
@Component
@Path("users")
public class UserApi {

    private final UserRepository userRepository;
    private final YouTubeApiClient youTubeApiClient;

    public UserApi(final UserRepository userRepository,
                   final YouTubeApiClient youTubeApiClient,
                   final AccessTokenRepository accessTokenRepository,
                   final YoutubeAuthService youtubeAuthService) {
        this.userRepository = userRepository;
        this.youTubeApiClient = youTubeApiClient;
    }

    @GET
    @Produces("application/json")
    public Response getUsers() {
        return Response.ok(userRepository.findAll()).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(User user) {
        userRepository.save(user);
        return Response.ok(user).build();
    }

    @GET
    @Path("/email/{email}")
    @Produces("application/json")
    public Response findByUsername(@PathParam("email") @NotNull String email) {
        User found = userRepository.findByEmail(email);
        Response response;
        if (found == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.ok(found).build();
        }

        return response;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findById(@PathParam("id") @NotNull String id) {
        User found = userRepository.findOne(id);
        Response response;
        if (found == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.ok(found).build();
        }

        return response;
    }

    @GET
    @Path("/exists")
    @Produces("application/json")
    public Response userWithSuchEmailExists(@QueryParam("email") @NotNull String email) {
        User found = userRepository.findByEmail(email);
        return found == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok().build();
    }

    @GET
    @Path("/{id}/youtube/subscribers")
    @Produces("application/json")
    public Response youtube(@PathParam("id") @NotNull String id) throws IOException {
        return Response.ok(youTubeApiClient.countSubscribers(id)).build();
    }

    @GET
    @Path("/{id}/youtube/subscribers/random")
    @Produces("application/json")
    public Response getRandomYoutubeSubscriber(@PathParam("id") @NotNull String id) throws IOException {
        return Response.ok(youTubeApiClient.getRandomSubscriber(id)).build();
    }


}
