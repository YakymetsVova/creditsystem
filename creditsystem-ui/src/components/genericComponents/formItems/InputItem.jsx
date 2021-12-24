const InputItem = (props) => {
    const classname = props.classname ? props.classname : "col-4";
    return (
        <div className={ classname }>
            <label>
                { props.name }
            </label>
            <input
                type={ props.fieldType }
                className="form-control"
                value={ props.attribute || '' }
                readOnly={ props.read_only }
                onChange={event =>  props.setAttribute(event.target.value)}
            />
        </div>
    )
}

export default InputItem;