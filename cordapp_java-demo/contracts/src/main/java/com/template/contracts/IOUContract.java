package com.template.contracts;

import com.template.states.IOUState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

import static net.corda.core.contracts.ContractsDSL.requireThat;


public class IOUContract implements Contract {

    public static final String ID = "com.template.contracts.IOUContract";


    @Override
    public void verify(LedgerTransaction tx) {




    }

    // Used to indicate the transaction's intent.
    public interface Commands extends CommandData {
        //In our hello-world app, We will only have one command.
        class Create implements Commands {}
    }
}