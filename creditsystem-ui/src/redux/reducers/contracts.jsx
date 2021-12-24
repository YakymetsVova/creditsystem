import {
    CONTRACT_CHANGED,
    CONTRACT_DELETED,
    CONTRACT_FETCH_SUCCESS,
    CONTRACTS_FETCH_SUCCESS,
    CONTRACT_ACTION_SUCCESS,
    FINISH_CONTRACT_ACTION,
    CONTRACT_ADDED_SUCCESS
} from '../types/types'

const initialState = {
    concreteContract: null,
    data: [],
    endedAction: false,
    shouldFetch: true
}

export default function contracts(state = initialState, action){
    const {type, payload} = action;
    switch (type) {
        case CONTRACTS_FETCH_SUCCESS:
            return {data: payload.contracts, shouldFetch: false};
        case CONTRACT_FETCH_SUCCESS:
            return {concreteContract: payload, shouldFetch: false};
        case CONTRACT_CHANGED:
            return {...state, shouldFetch: true}
        case CONTRACT_ADDED_SUCCESS:
            return {...state, shouldFetch: true}
        case CONTRACT_DELETED:
            return {...state, shouldFetch: true}
        case CONTRACT_ACTION_SUCCESS:
            return {...state, endedAction: true}
        case FINISH_CONTRACT_ACTION:
            return {...state, endedAction: false}
        default:
            return state;
    }
}