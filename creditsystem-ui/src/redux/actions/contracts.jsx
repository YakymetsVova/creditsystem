import {
    CONTRACT_CHANGED,
    CONTRACT_DELETED,
    CONTRACT_FETCH_SUCCESS,
    CONTRACTS_FETCH_SUCCESS,
    CONTRACT_ACTION_SUCCESS,
    CONTRACT_ADDED_SUCCESS,
} from '../types/types'

import ContractService from "../services/contractsService";

export const fetchContracts = () => async (dispatch) => {
    try {
        const data = await ContractService.getAll()
        dispatch({
            type: CONTRACTS_FETCH_SUCCESS,
            payload: {contracts: data}
        });
    } catch (error) {
        console.log('error fetching contracts', error);
    }
};

export const createContract = (data) => async (dispatch) => {
    try {
        await ContractService.create(data)
        console.log("test");
        dispatch({
            type: CONTRACT_ADDED_SUCCESS,
        });
        dispatch({
            type: CONTRACT_ACTION_SUCCESS
        });
    } catch (error) {
        console.log('error creating contract', error);
    }
};

export const updateContract = (data) => async (dispatch) => {
    try {
        await ContractService.update(data)
        dispatch({
            type: CONTRACT_CHANGED,
        });
        dispatch({
            type: CONTRACT_ACTION_SUCCESS
        });
    } catch (error) {
        console.log('error updating contract', error);
    }
};

export const fetchContract = (id) => async (dispatch) => {
    try {
        const data = await ContractService.get(id)
        dispatch({
            type: CONTRACT_FETCH_SUCCESS,
            payload: data,
        });
    } catch (error) {
        console.log('error fetching contract', error);
    }
};

export const deleteContract = (id) => async (dispatch) => {
    try {
        await ContractService.remove(id);
        const data = await ContractService.getAll();
        dispatch({
            type: CONTRACT_DELETED,
            payload: data
        });
    } catch (error) {
        console.log('error deleting contract', error.response.data);
    }
}