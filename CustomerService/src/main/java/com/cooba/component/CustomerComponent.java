package com.cooba.component;

import com.cooba.dto.request.CustomerEnterRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.response.CustomerAgentSearchResponse;
import com.cooba.dto.response.CustomerEnterResponse;
import com.cooba.dto.response.LoginResponse;
import com.cooba.dto.response.RegisterResponse;

public interface CustomerComponent {

    RegisterResponse create(RegisterRequest request);

    CustomerEnterResponse enterRoom(CustomerEnterRequest request);

    CustomerAgentSearchResponse searchAgent();

    void createGuest(int number);

    LoginResponse getGuestToken();

}
