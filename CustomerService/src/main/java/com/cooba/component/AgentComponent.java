package com.cooba.component;

import com.cooba.dto.request.AgentCreateRequest;
import com.cooba.dto.request.AgentDisableRequest;
import com.cooba.dto.request.AgentSearchRequest;
import com.cooba.dto.request.AgentUpdateRequest;
import com.cooba.dto.response.AgentCreateResponse;
import com.cooba.dto.response.AgentDisableResponse;
import com.cooba.dto.response.AgentSearchResponse;
import com.cooba.dto.response.AgentUpdateResponse;

public interface AgentComponent {

    AgentCreateResponse createAgent(AgentCreateRequest request);

    AgentUpdateResponse updateAgent(AgentUpdateRequest request);

    AgentDisableResponse disableAgent(AgentDisableRequest request);

    AgentSearchResponse searchAgent(AgentSearchRequest request);

    void searchCustomer();

    void searchCustomerTicket();

    void transferTicket();

    void manageCustomer();

}
