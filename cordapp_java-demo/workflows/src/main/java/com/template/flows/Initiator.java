package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.IOUContract;
import com.template.states.IOUState;
import net.corda.core.contracts.Command;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

@InitiatingFlow
@StartableByRPC
public class Initiator extends FlowLogic<Void>{


    private final int iouValue;
    private final Party otherParty;

    private final ProgressTracker progresstracker=new ProgressTracker();

    public Initiator( int iouValue, Party otherParty) {
        this.iouValue = iouValue;
       // this.iouvalue = iouvalue;
        this.otherParty = otherParty;
    }
    @Suspendable
    @Override
    public Void call() throws FlowException {
        final Party notary=getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        final IOUState output= new IOUState(iouValue,getOurIdentity(),otherParty);

        Command command=new Command<>(new IOUContract.Commands.Create(), getOurIdentity().getOwningKey() );


        final TransactionBuilder builder= new TransactionBuilder();
        builder.addOutputState(output);
        builder.addCommand(command);

        final SignedTransaction signedTX=getServiceHub().signInitialTransaction(builder);

        FlowSession otherPartySession =initiateFlow(otherParty);
        subFlow(new FinalityFlow(signedTX,otherPartySession));

        return null;
    }
}