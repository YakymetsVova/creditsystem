import React from "react";
import FormHeader from "./FormHeader";

const ItemsFormWrapper = (props) => {
    return (
        <div className="container bg-white p-5 rounded">
            <form onSubmit={ props.submit }>
                <FormHeader isSubmitEnabled={props.isSubmitEnabled}
                            submitValue={ props.isDetailPage !== false ? "Save" : "Create" }
                            header={ props.header }
                            cancelLink={props.cancelLink}/>
                { props.contentForm }
            </form>
        </div>
    )
}

export default ItemsFormWrapper;