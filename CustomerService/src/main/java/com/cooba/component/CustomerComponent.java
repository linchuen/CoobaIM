package com.cooba.component;

import com.cooba.dto.request.CustomerDetailRequest;
import com.cooba.dto.request.CustomerEnterRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.response.*;

public interface CustomerComponent {

    RegisterResponse create(RegisterRequest request);

    CustomerEnterResponse enterRoom(CustomerEnterRequest request);

    CustomerAgentSearchResponse searchAgent();

    void createGuest(int number);

    LoginResponse getGuestToken();

    CustomerDetailResponse getDetails(CustomerDetailRequest request);

}
