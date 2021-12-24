import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux'
import CreateButton from '../../components/helpers/buttons/CreateButton'
import Catalog from '../../components/genericComponents/catalog/Catalog'
import {deleteCredit, fetchCredits, fetchCreditsWithFilter} from '../../redux/actions/credits';

const selectCredits = state => state.credits.data
const shouldFetch = state => state.credits.shouldFetch;
const selectRole = state => state.auth.userRole;

const CreditsCatalog = () => {
    const dispatch = useDispatch();
    const credits = useSelector(selectCredits);
    const fetch = useSelector(shouldFetch);
    const role = useSelector(selectRole);

    const tableHeaders = [
        {text: 'ID', dataProp: 'id'},
        {text: 'Credit name', dataProp: 'name'},
        {text: 'Max sum', dataProp: 'maxSum'},
        {text: 'Min sum', dataProp: 'minSum'},
        {text: 'Months duration', dataProp: 'monthsDuration'},
        {text: 'Earning percentage', dataProp: 'earningPercentage'},
        {text: 'Earning percentage after deadline', dataProp: 'earningPercentageAfterDeadline'}
    ];

    useEffect(() => {
        dispatch(fetchCredits())
    }, [fetch]);

    const fetchCreditsFiltered = (data) => {
        dispatch(fetchCreditsWithFilter(data));
    }

    return (
        <Catalog
            deleteAction={deleteCredit}
            detailUrl="/credits/detail"
            title={'Credits catalog'}
            actionComponent={role === "CreditProvider" && <CreateButton link="/credits/create" text="Create new credit"/>}
            headers={tableHeaders}
            data={credits}
            withSearch={role === "Customer"}
            fetchData={fetchCreditsFiltered}
            userRole={role}
        >
        </Catalog>
    )
}

export default CreditsCatalog;