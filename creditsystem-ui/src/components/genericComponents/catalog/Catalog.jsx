import React, {useState} from "react";
import {useHistory} from "react-router-dom";
import classNames from "classnames";
import './catalog.css';
import {useDispatch} from "react-redux";
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";


const ConfirmationModal = ({isOpen, toggle, deleteItemAction, itemToDelete}) => {
    const dispatch = useDispatch();
    return (
        <Modal isOpen={isOpen} toggle={toggle}>
            <ModalHeader>Do you really want to delete this item?</ModalHeader>
            <ModalBody>
                Press "Yes" to perform deletion
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={() => {
                    dispatch(deleteItemAction(itemToDelete.id));
                    toggle()
                }}>Yes</Button>
                <Button color="secondary" onClick={toggle}>No</Button>
            </ModalFooter>
        </Modal>
    )
}

const Catalog = ({
                     deleteAction = null,
                     detailUrl = null,
                     title = '',
                     actionComponent = null,
                     headers = [],
                     data = [],
                     withSearch = false,
                     fetchData = null,
                     userRole
                 } = {}) => {
    const [toDeleteId, setToDeleteId] = useState();
    const history = useHistory();
    const [isModalOpen, setIsModalOpen] = useState(false);

    const [maxSum, setMaxSum] = useState('');
    const [minSum, setMinSum] = useState('');
    const [monthsDuration, setMonthsDuration] = useState('');
    const [earningPercentage, setEarningPercentage] = useState('');
    const [earningPercentageAfterDeadline, setEarningPercentageAfterDeadline] = useState('');

    const toggle = () => setIsModalOpen(!isModalOpen);



    return (
        <div className="container-fluid bg-white rounded mb-5">
            <div className="row pt-4 pb-4">
                <div className="col-5 ps-5">
                    <div className="fs-3 fw-bold header-style">{title} </div>
                </div>
                <div className="col-7 ">
                    <div className={classNames("d-flex flew-wrap", {
                        "justify-content-between": withSearch,
                        "justify-content-end": !withSearch
                    })}>
                        {withSearch && userRole === "Customer" &&
                        <div className="input-group w-100">
                            <input
                                type="text"
                                className="form-control"
                                value={maxSum}
                                placeholder="Max sum"
                                onChange={(e) => {
                                    setMaxSum(e.target.value)
                                }}
                            />
                            <input
                                type="text"
                                className="form-control"
                                value={minSum}
                                placeholder="Min sum"
                                onChange={(e) => {
                                    setMinSum(e.target.value)
                                }}
                            />
                            <input
                                type="text"
                                className="form-control"
                                value={monthsDuration}
                                placeholder="Months duration"
                                onChange={(e) => {
                                    setMonthsDuration(e.target.value)
                                }}
                            />
                            <input
                                type="text"
                                className="form-control"
                                value={earningPercentage}
                                placeholder="Percentage"
                                onChange={(e) => {
                                    setEarningPercentage(e.target.value)
                                }}
                            />
                            <input
                                type="text"
                                className="form-control w-25"
                                value={earningPercentageAfterDeadline}
                                placeholder="Percentage after deadline"
                                onChange={(e) => {
                                    setEarningPercentageAfterDeadline(e.target.value)
                                }}
                            />
                            <button className="input-group-text" onClick={() => fetchData({
                                maxSum,
                                minSum,
                                monthsDuration,
                                earningPercentage,
                                earningPercentageAfterDeadline
                            })}><i
                                className="fa fa-search"/></button>
                        </div>}
                        {actionComponent}
                    </div>
                </div>
            </div>

            <div className="wrapper">
                <table className="table align-middle ">
                    <thead>
                    <tr>
                        {headers.map(e => (
                            <th className="header-cols" key={e.text} style={{"width": 1 / headers.length}}
                                scope="col">{e.text}</th>))}
                        {<th className="delete-col" scope="col"/>}
                    </tr>
                    </thead>
                    <tbody>
                    {data !== [] && data.map(e => (
                        <tr
                            style={{"cursor": "pointer"}}
                            key={e.id}
                            onClick={() => detailUrl != null ? history.push(`${detailUrl}/${e.id}`) : null}>
                            {headers.map((h, i) => (
                                h.dataProp === 'status' ?
                                    <td className="table-cols" key={e.id + "status" + i}>
                                        <div className="status">
                                            {e[h.dataProp]}
                                        </div>
                                    </td>
                                    :
                                    <td className="table-cols" key={e.id + "not_status" + i}>{e[h.dataProp]}</td>
                            ))}
                            {<td className="table-cols" key={"delete_button_column" + e.id}>
                                <button className="delete-btn btn"
                                        onClick={(elem) => {
                                            setToDeleteId(e);
                                            setIsModalOpen(true);
                                            elem.stopPropagation();
                                        }}
                                >
                                    <i className="fa fa-fw fa-trash fa-2x"/>
                                </button>
                            </td>}
                        </tr>
                    ))}
                    <ConfirmationModal isOpen={isModalOpen} toggle={toggle}
                                       deleteItemAction={deleteAction} itemToDelete={toDeleteId}/>
                    </tbody>
                </table>
                {data.length === 0 &&
                <div className="row text-center p-3">
                    <div className="col-12">
                        <p className="text-muted">There are no items added to the catalog</p>
                    </div>
                </div>
                }
            </div>
        </div>
    );
};


export default Catalog;