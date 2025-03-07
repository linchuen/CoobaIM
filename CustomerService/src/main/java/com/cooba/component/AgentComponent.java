package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.*;

public interface AgentComponent {

    AgentCreateResponse createAgent(AgentCreateRequest request);

    AgentUpdateResponse updateAgent(AgentUpdateRequest request);

    AgentDisableResponse disableAgent(AgentDisableRequest request);

    AgentSearchResponse searchAgent(AgentSearchRequest request);

    CustomerSearchResponse searchCustomer();

    CustomerTicketSearchResponse searchCustomerTicket(CustomerTicketSearchRequest request);

    TicketTransferResponse transferTicket(TicketTransferRequest request);

    void bindCustomer(AgentCustomerRequest request);

    void unbindCustomer(AgentCustomerRequest request);

}
