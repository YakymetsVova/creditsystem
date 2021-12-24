import { Link } from "react-router-dom";
import React from "react";
import './styles.css';

const FormHeader = (props) => {
    return (
        <>
            <div className="row">
                <div className="col-lg-10 col-md-8">
                    <p className="h2 font-weight-bold header-detail">{props.header}</p>
                </div>
                {
                    <div className="col-2">
                        <Link to={props.cancelLink}
                              className="btn btn-outline-secondary">
                            Cancel
                        </Link>
                        <input
                            className="btn btn-dark mt-2 mt-xl-0 ms-xl-2"
                            type="submit"
                            value={props.submitValue} />
                    </div>}
            </div>
            <hr className="bg-black border-1 border-top border border-dark" />
        </>
    )
}
export default FormHeader;