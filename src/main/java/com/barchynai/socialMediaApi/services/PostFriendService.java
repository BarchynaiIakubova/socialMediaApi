package com.barchynai.socialMediaApi.services;

import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.enums.FriendStatus;
import com.barchynai.socialMediaApi.models.Friend;
import com.barchynai.socialMediaApi.models.users.User;
import com.barchynai.socialMediaApi.repositories.FriendRepository;
import com.barchynai.socialMediaApi.repositories.UserRepository;
import com.barchynai.socialMediaApi.validations.UserValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFriendService {

    private  final UserRepository userRepository;

    private final FriendRepository friendRepository;
    private final UserValidate userValidate;


    public Response sendFriendRequest(Long receiverId) {

        User sender = userValidate.getByAuthentication();
        User receiver = userValidate.findById(receiverId);

        if (sender == receiver) {

            throw new IllegalStateException("You cannot send friend request to yourself");
        }


        Friend friendRequest = Friend.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendStatus.PENDING)
                .sentDate(new Date())
                .build();

        friendRepository.save(friendRequest);

        return new Response("Friend request successfully sent");
    }


    public Response receiveFriendRequest(Long requestedId) {

        User friendRequest = userValidate.findById(requestedId);
        System.out.println("Friend Request is: " + friendRequest);

        List<Friend> receiver = friendRequest.getReceivedRequests();

        for (Friend r: receiver) {
            System.out.println("The id of receiver is: " + r.getReceiver());
            if (r.getReceiver().equals(receiver) && r.getStatus() == FriendStatus.PENDING) {
                r.setStatus(FriendStatus.ACCEPTED);
                friendRepository.save(r);
            }
        }
        return new Response("The request is successfully accepted");
    }



    public Response rejectFriendRequest(Long requestedId) {

        User  receiver= userValidate.getByAuthentication();
        User friendRequest = userValidate.findById(requestedId);
        System.out.println("Friend Request is: " + friendRequest);

        List<Friend> receivers = friendRequest.getReceivedRequests();

        for (Friend r: receivers) {
            System.out.println("The id of receiver is: " + r.getReceiver());
            if (r.getReceiver().getId() == receiver.getId() && r.getStatus() == FriendStatus.PENDING) {

                r.setStatus(FriendStatus.REJECTED);
                friendRepository.save(r);
            }
        }
        return new Response("The request is successfully rejected");
    }
}
