import {
    CREDIT_CHANGED,
    CREDIT_DELETED,
    CREDIT_FETCH_SUCCESS,
    CREDITS_FETCH_SUCCESS,
    CREDIT_ACTION_SUCCESS,
    CREDIT_ADDED_SUCCESS,
} from '../types/types'

import CreditService from "../services/creditsService";

export const fetchCredits = () => async (dispatch) => {
    try {
        const data = await CreditService.getAll();
        dispatch({
            type: CREDITS_FETCH_SUCCESS,
            payload: {credits: data},
        });
    } catch (error) {
        console.log('error fetching credits', error);
    }
};

export const fetchCreditsWithFilter = (data) => async (dispatch) => {
    try {
        const result = await CreditService.getFiltered({...data});
        dispatch({
            type: CREDITS_FETCH_SUCCESS,
            payload: {credits: result},
        });
    }
    catch (error) {
        console.log('error fetching credits with filters', error);
    }
}

export const createCredit = (data) => async (dispatch) => {
    try {
        await CreditService.create(data)
        dispatch({
            type: CREDIT_ADDED_SUCCESS,
        });
        dispatch({
            type: CREDIT_ACTION_SUCCESS
        });
    } catch (error) {
        console.log('error creating credit', error);
    }
};

export const updateCredit = (data) => async (dispatch) => {
    try {
        await CreditService.update(data)
        dispatch({
            type: CREDIT_CHANGED,
        });
        dispatch({
            type: CREDIT_ACTION_SUCCESS
        });
    } catch (error) {
        console.log('error updating credit', error);
    }
};

export const fetchCredit = (id) => async (dispatch) => {
    try {
        const data = await CreditService.get(id)
        dispatch({
            type: CREDIT_FETCH_SUCCESS,
            payload: data,
        });
    } catch (error) {
        console.log('error fetching credit', error);
    }
};

export const deleteCredit = (id) => async (dispatch) => {
    try {
        await CreditService.remove(id);
        const data = await CreditService.getAll();
        dispatch({
            type: CREDIT_DELETED,
            payload: data
        });
    } catch (error) {
        console.log('error deleting credit', error.response.data);
    }
}