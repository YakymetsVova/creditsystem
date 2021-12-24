import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux'
import Catalog from '../../components/genericComponents/catalog/Catalog'
import {deleteContract, fetchContracts} from "../../redux/actions/contracts";

const selectContracts= state => state.contracts.data
const shouldFetch = state => state.contracts.shouldFetch;
const selectRole = state => state.auth.userRole;

const ContractsCatalog = () => {
    const dispatch = useDispatch();
    const contracts = useSelector(selectContracts);
    const fetch = useSelector(shouldFetch);
    const role = useSelector(selectRole);

    const tableHeaders = [
        {text: 'ID', dataProp: 'id'},
        {text: 'Borrower name', dataProp: 'customerName'},
        {text: 'Credit name', dataProp: 'creditName'},
        {text: 'Months duration', dataProp: 'monthsDuration'},
        {text: 'Earning percentage', dataProp: 'earningPercentage'},
        {text: 'Earning percentage after deadline', dataProp: 'earningPercentageAfterDeadline'},
        {text: 'Payed sum', dataProp: 'payedSum'},
        {text: 'Owed sum', dataProp: 'owedSum'},
        {text: 'Deadline', dataProp: 'deadline'},
        {text: 'Sum to pay', dataProp: 'sumToPay'}
    ];

    useEffect(() => {
        dispatch(fetchContracts())
    }, [fetch]);

    const frontendReadableContracts = contracts && contracts.map(contract => ({
        id: contract.id,
        customerName: contract.customer.firstName + " " + contract.customer.lastName,
        creditName: contract.credit.name,
        monthsDuration: contract.credit.monthsDuration,
        earningPercentage: contract.credit.earningPercentage,
        earningPercentageAfterDeadline: contract.credit.earningPercentageAfterDeadline,
        payedSum: contract.payedSum,
        owedSum: contract.owedSum,
        deadline: new Date(contract.deadline).toDateString() ,
        sumToPay: contract.sumToPay
    })  );

    return (
        <Catalog
            deleteAction={deleteContract}
            detailUrl="/contracts/detail"
            title={'Contracts catalog'}
            headers={tableHeaders}
            data={frontendReadableContracts}
        >
        </Catalog>
    )
}

export default ContractsCatalog;