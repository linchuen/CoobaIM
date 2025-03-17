package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.*;

public interface AgentComponent {

    AgentCreateResponse createAgent(AgentCreateRequest request);

    void updateAgent(AgentUpdateRequest request);

    void disableAgent(AgentDisableRequest request);

    AgentSearchResponse searchAgent(AgentSearchRequest request);

    CustomerSearchResponse searchBindCustomer(BindCustomerSearchRequest request);

    CustomerTicketSearchResponse searchCustomerTicket(CustomerTicketSearchRequest request);

    TicketSearchResponse searchRecentTicket();

    TicketSearchResponse searchTicket(TicketSearchRequest request);

    TicketTransferResponse transferTicket(TicketTransferRequest request);

    CustomerBindResponse bindCustomer(AgentCustomerRequest request);

    void unbindCustomer(AgentCustomerRequest request);

}
