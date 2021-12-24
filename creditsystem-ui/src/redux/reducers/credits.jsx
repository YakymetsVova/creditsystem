import {
    CREDIT_CHANGED,
    CREDIT_DELETED,
    CREDIT_FETCH_SUCCESS,
    CREDITS_FETCH_SUCCESS,
    CREDIT_ACTION_SUCCESS,
    FINISH_CREDIT_ACTION,
    CREDIT_ADDED_SUCCESS
} from '../types/types'

const initialState = {
    concreteCredit: null,
    data: [],
    endedAction: false,
    shouldFetch: true
}

export default function credits(state = initialState, action){
    const {type, payload} = action;
    switch (type) {
        case CREDITS_FETCH_SUCCESS:
            return {data: payload.credits, shouldFetch: false};
        case CREDIT_FETCH_SUCCESS:
            return {concreteCredit: payload, shouldFetch: false};
        case CREDIT_CHANGED:
            return {...state, shouldFetch: true}
        case CREDIT_ADDED_SUCCESS:
            return {...state, shouldFetch: true}
        case CREDIT_DELETED:
            return {...state, shouldFetch: true}
        case CREDIT_ACTION_SUCCESS:
            return {...state, endedAction: true}
        case FINISH_CREDIT_ACTION:
            return {...state, endedAction: false}
        default:
            return state;
    }
}