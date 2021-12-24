import InputItem from "../../../components/genericComponents/formItems/InputItem";
import React from "react";

const ContractForm = ({
                          id,
                          creditName,
                          customerName,
                          monthsDuration,
                          earningPercentage,
                          earningPercentageAfterDeadline,
                          payedSum,
                          setPayedSum,
                          owedSum,
                          setOwedSum,
                          deadline,
                          sumToPay,
                          maxSum,
                          userRole
                      }) => {
    return (
        <>
            <div className="row mb-2">
                {id !== undefined ? <InputItem name="ID" read_only={true} attribute={id}/> : null}
                <InputItem fieldType="other"
                           name="Credit name"
                           attribute={creditName}
                           read_only={true}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Customer name"
                           attribute={customerName}
                           read_only={true}
                />
                <InputItem fieldType="other"
                           name="Months duration"
                           attribute={monthsDuration}
                           read_only={true}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Earning percentage"
                           attribute={earningPercentage}
                           read_only={true}
                />
                <InputItem fieldType="other"
                           name="Earning percentage after deadline"
                           attribute={earningPercentageAfterDeadline}
                           read_only={true}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Pay some sum"
                           attribute={payedSum}
                           setAttribute={setPayedSum}
                           read_only={userRole !== "Customer"}
                />
                <InputItem fieldType="other"
                           name="Owed sum"
                           attribute={owedSum}
                           setAttribute={setOwedSum}
                           read_only={userRole !== "Customer"}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Deadline"
                           attribute={deadline}
                           read_only={true}
                />
                <InputItem fieldType="other"
                           name="Sum to pay"
                           attribute={sumToPay}
                           read_only={true}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Max possible sum to owe"
                           attribute={maxSum}
                           read_only={true}
                />
            </div>
        </>
    )
}

export default ContractForm;