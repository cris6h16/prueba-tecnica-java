package org.example.Application.Exceptions.Impls;

import org.example.Application.Exceptions.AbstractApplicationException;

public class AlreadyFollowingException extends AbstractApplicationException {
    public AlreadyFollowingException(String followerUsername, String followedUsername) {
        super(followerUsername + " ya está siguiendo a @" + followedUsername);
    }
}

