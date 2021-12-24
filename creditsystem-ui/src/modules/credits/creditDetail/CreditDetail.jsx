import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux'
import {useHistory, useParams} from "react-router-dom"
import ItemsFormWrapper from "../../../components/genericComponents/formItems/ItemsFormWrapper";
import CreditForm from "./CreditForm";
import {createCredit, fetchCredit, updateCredit} from "../../../redux/actions/credits";
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import InputItem from "../../../components/genericComponents/formItems/InputItem";
import {createContract} from "../../../redux/actions/contracts";

const ContractModal = ({isOpen, toggle, createContractAction, credit}) => {
    const dispatch = useDispatch();
    const history = useHistory();
    const [owedSum, setOwedSum] = useState();

    return (
        <Modal isOpen={ isOpen } toggle={ toggle }>
            <ModalHeader>Enter amount you want to owe:</ModalHeader>
            <ModalBody>
                <InputItem fieldType="other"
                           name="Sum to owe"
                           attribute={ owedSum }
                           setAttribute={ setOwedSum }
                           classname="col-12"
                />
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={ () => {
                    dispatch(createContractAction({credit, owedSum}));
                    toggle();
                    history.push("/credits");
                } }>Submit</Button>
                <Button color="secondary" onClick={ toggle }>Cancel</Button>
            </ModalFooter>
        </Modal>
    )
}


const selectCredit = state => state.credits.concreteCredit;
const selectEndedAction = state => state.credits.endedAction;
const selectRole = state => state.auth.userRole;

const CreditDetail = () => {
    const dispatch = useDispatch();
    const history = useHistory();
    const credit = useSelector(selectCredit);
    const endedAction = useSelector(selectEndedAction);
    const userRole = useSelector(selectRole);

    const [name, setName] = useState();
    const [maxSum, setMaxSum] = useState();
    const [minSum, setMinSum] = useState();
    const [monthsDuration, setMonthsDuration] = useState();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [earningPercentage, setEarningPercentage] = useState();
    const [earningPercentageAfterDeadline, setEarningPercentageAfterDeadline] = useState();

    const toggle = () => setIsModalOpen(! isModalOpen);

    const {id} = useParams();

    useEffect(() => {
        if (id !== undefined) {
            dispatch(fetchCredit(id))
        }
    }, []);

    useEffect(() => {
        if (endedAction) {
            history.push('/credits')
        }
    }, [endedAction])

    useEffect(() => {
        setName(credit ? credit.name : '');
        setMaxSum(credit ? credit.maxSum : 0);
        setMinSum(credit ? credit.minSum : 0);
        setMonthsDuration(credit ? credit.monthsDuration : 0);
        setEarningPercentage(credit ? credit.earningPercentage : 0);
        setEarningPercentageAfterDeadline(credit ? credit.earningPercentageAfterDeadline : 0);
    }, [credit])


    const submitCreditProvider = (e) => {
        e.preventDefault();
        if (id !== undefined) {
            dispatch(updateCredit({id, name, maxSum, minSum, monthsDuration, earningPercentage, earningPercentageAfterDeadline}));
        } else {
            dispatch(createCredit({name, maxSum, minSum, monthsDuration, earningPercentage, earningPercentageAfterDeadline}));
        }
    }

    const submitCustomer = (e) => {
        e.preventDefault();
        setIsModalOpen(true);
    }

    const properties = {
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
        setEarningPercentageAfterDeadline
    };

    return (
        <>
            <ItemsFormWrapper
                isDetailPage={!!id}
                submit={userRole === "CreditProvider" ? submitCreditProvider : submitCustomer}
                contentForm={CreditForm({...properties, userRole})}
                header={id ? name : "Create new credit"}
                cancelLink="/credits">
            </ItemsFormWrapper>
            <ContractModal createContractAction={createContract} credit={credit} isOpen={isModalOpen} toggle={toggle}/>
        </>

    )
}

export default CreditDetail;