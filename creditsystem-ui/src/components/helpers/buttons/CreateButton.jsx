import React from "react";
import { Link } from "react-router-dom";

const CreateButton = ({ link=null, text=null }) => {
    return (
        <Link to={link} style={{ textDecoration: 'none' }}>
            <button type="button" className="btn btn-dark d-flex align-items-center">
                <div
                    className="rounded-circle bg-light text-dark text-light fw-bold"
                    style={{width: '24px', height: '24px'}}
                >
                    +
                </div>
                <div className="ms-2">{text}</div>
            </button>
        </Link>
    );
};

export default CreateButton;