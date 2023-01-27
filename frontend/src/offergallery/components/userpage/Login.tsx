import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import "../../../styles/components/userpage/Login.css"

type LoginProps = {
    login: (name: string, password: string) => Promise<string>
}

export default function Login(props: LoginProps){

    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const navigate = useNavigate()

    function onChangeName(event: ChangeEvent<HTMLInputElement>){
        setName(event.target.value)
    }

    function onChangePassword(event: ChangeEvent<HTMLInputElement>){
        setPassword(event.target.value)
    }

    function onLoginSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        props.login(name,password)
            .then(user=>{
                navigate("/")
            })
    }

    return(
        <div className="login-container">
            <div className="login-header">
                <h4>Login</h4>
            </div>
            <div className="login-form-container">
                <form onSubmit={onLoginSubmit} className="login-form">
                    <div className="form-group">
                        <input onChange={onChangeName}
                               value={name}
                               type="text"
                               placeholder="Name"
                               className="form-control"
                        />
                        <label className="form-label">Name</label>
                    </div>
                    <div className="form-group">
                        <input onChange={onChangePassword}
                               value={password}
                               type="password"
                               placeholder="Password"
                               className="form-control"
                        />
                        <label className="form-label">Password</label>
                    </div>
                    <div className="form-group">
                        <button type={"submit"} className="form-submit-button">Login</button>
                    </div>
                </form>
            </div>
        </div>
    )
}