package com.cooba.component;

import com.cooba.dto.request.AgentCreateRequest;
import com.cooba.dto.request.AgentDisableRequest;
import com.cooba.dto.request.AgentSearchRequest;
import com.cooba.dto.request.AgentUpdateRequest;
import com.cooba.dto.response.*;

public interface AgentComponent {

    AgentCreateResponse createAgent(AgentCreateRequest request);

    AgentUpdateResponse updateAgent(AgentUpdateRequest request);

    AgentDisableResponse disableAgent(AgentDisableRequest request);

    AgentSearchResponse searchAgent(AgentSearchRequest request);

    CustomerSearchResponse searchCustomer();

    void searchCustomerTicket();

    void transferTicket();

    void manageCustomer();

}
