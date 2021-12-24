import InputItem from "../../../components/genericComponents/formItems/InputItem";
import React from "react";

const CreditForm = ({
                        id,
                        name,
                        setName,
                        maxSum,
                        setMaxSum,
                        minSum,
                        setMinSum,
                        monthsDuration,
                        setMonthsDuration,
                        earningPercentage,
                        setEarningPercentage,
                        earningPercentageAfterDeadline,
                        setEarningPercentageAfterDeadline,
                        userRole
                    }) => {
    return (
        <>
            <div className="row mb-2">
                {id !== undefined ? <InputItem name="ID" read_only={true} attribute={id}/> : null}
                <InputItem fieldType="other"
                           name="Credit name"
                           attribute={name}
                           setAttribute={setName}
                           read_only={userRole === "Customer"}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Max sum"
                           attribute={maxSum}
                           setAttribute={setMaxSum}
                           read_only={userRole === "Customer"}
                />
                <InputItem fieldType="other"
                           name="Min sum"
                           attribute={minSum}
                           setAttribute={setMinSum}
                           read_only={userRole === "Customer"}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Months duration"
                           attribute={monthsDuration}
                           setAttribute={setMonthsDuration}
                           read_only={userRole === "Customer"}
                />
                <InputItem fieldType="other"
                           name="Earning percentage"
                           attribute={earningPercentage}
                           setAttribute={setEarningPercentage}
                           read_only={userRole === "Customer"}
                />
            </div>
            <div className="row mb-2">
                <InputItem fieldType="other"
                           name="Earning percentage after deadline"
                           attribute={earningPercentageAfterDeadline}
                           setAttribute={setEarningPercentageAfterDeadline}
                           read_only={userRole === "Customer"}
                />
            </div>

        </>
    )
}

export default CreditForm;