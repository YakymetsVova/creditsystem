import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux'
import {useHistory, useParams} from "react-router-dom"
import ItemsFormWrapper from "../../../components/genericComponents/formItems/ItemsFormWrapper";
import {fetchContract, updateContract} from "../../../redux/actions/contracts";
import ContractForm from "./ContractForm";


const selectContract = state => state.contracts.concreteContract;
const selectEndedAction = state => state.contracts.endedAction;
const selectRole = state => state.auth.userRole;

const ContractDetail = () => {
    const dispatch = useDispatch();
    const history = useHistory();
    const contract = useSelector(selectContract);
    const endedAction = useSelector(selectEndedAction);
    const userRole = useSelector(selectRole);

    const [creditName, setCreditName] = useState();
    const [customerName, setCustomerName] = useState();
    const [monthsDuration, setMonthsDuration] = useState();
    const [earningPercentage, setEarningPercentage] = useState();
    const [earningPercentageAfterDeadline, setEarningPercentageAfterDeadline] = useState();
    const [payedSum, setPayedSum] = useState();
    const [owedSum, setOwedSum] = useState();
    const [deadline, setDeadline] = useState();
    const [sumToPay, setSumToPay] = useState();
    const [maxSum, setMaxSum] = useState();

    useEffect(() => {
        setCreditName(contract ? contract.credit.name : "")
        setCustomerName(contract ? contract.customer.firstName + " " + contract.customer.lastName : "");
        setMonthsDuration(contract ? contract.credit.monthsDuration : "");
        setEarningPercentage(contract ? contract.credit.earningPercentage : "");
        setEarningPercentageAfterDeadline(contract ? contract.credit.earningPercentageAfterDeadline : "");
        setPayedSum( 0);
        setOwedSum(contract ? contract.owedSum: "");
        setDeadline(contract ? new Date(contract.deadline).toDateString() : "");
        setSumToPay(contract ? contract.sumToPay : "");
        setMaxSum(contract ? contract.credit.maxSum : "");
    }, [contract])

    const {id} = useParams();

    useEffect(() => {
        if (id !== undefined) {
            dispatch(fetchContract(id))
        }
    }, []);

    useEffect(() => {
        if (endedAction) {
            history.push('/contracts')
        }
    }, [endedAction])

    useEffect(() => {

    }, [contract])


    const submit = (e) => {
        e.preventDefault();
        if (id !== undefined) {
            dispatch(updateContract({id, payedSum, owedSum}));
        }
    }


    const properties = {
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
    }


    return (
            <ItemsFormWrapper
                isDetailPage={!!id}
                submit={submit}
                contentForm={ContractForm({...properties, userRole})}
                header={`Contract of ${customerName}`}
                cancelLink="/contracts">
            </ItemsFormWrapper>
    )
}

export default ContractDetail;