package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;

// ******************
// * Responder flow *
// ******************
@InitiatedBy(Initiator.class)
public class Responder extends FlowLogic<Void> {

    //private variable
    private FlowSession OtherpartySession;

    //Constructor
    public Responder(FlowSession counterpartySession) {
        this.OtherpartySession = counterpartySession;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {

        //Stored the transaction into data base.
        subFlow(new ReceiveFinalityFlow(OtherpartySession));
        return null;
    }
}
