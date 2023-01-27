import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import "../../../styles/components/userpage/Register.css"

type RegisterProps = {
    register: (user: string, password: string, email: string)=>void
}

const Register = (props: RegisterProps) => {

    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [email, setEmail] = useState<string>("")

    const navigation = useNavigate()

    function onChangeUser(event: ChangeEvent<HTMLInputElement>){
        setUsername(event.target.value)
    }

    function onChangeEmail(event: ChangeEvent<HTMLInputElement>){
        setEmail(event.target.value)
    }

    function onChangePassword(event: ChangeEvent<HTMLInputElement>){
        setPassword(event.target.value)
    }

    function onRegisterSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        props.register(username,password,email)
        setUsername("")
        setPassword("")
        setEmail("")
        navigation("/login")
    }

    return(
        <div className="register-container">
            <div className="register-title">
                <h4 className="register-heading">Register</h4>
            </div>
            <div className="register-form-container">
                <form onSubmit={onRegisterSubmit} className="register-form">
                    <div className="form-group">
                        {username.length >= 1 ?
                            <input onChange={onChangeUser}
                                   className="form-control"
                                   value={username}
                                   type="text"
                                   placeholder="Name"/> :
                            <input type="test"
                                    onChange={onChangeUser}
                                    className="form-control"
                                    placeholder="Name"
                                    value={username}/>
                        }
                        <label className="form-label">Name</label>
                    </div>
                    <div className="form-group">
                        {password.length <= 8 ?
                        <input onChange={onChangePassword}
                                value={password}
                                type="password"
                                placeholder="Password"/>:
                        <input onChange={onChangePassword}
                                value={password}
                                type="password"
                                placeholder={"Password"}/>
                        }
                        <label className="form-label">Password</label>
                        <div>
                            <p className="password-text">The Password needs at least 8 Characters.</p>
                        </div>
                        <div className="form-group">
                            {email.includes('@') ?
                                <input onChange={onChangeEmail}
                                       className="form-control"
                                       value={email}
                                       type="email"
                                       placeholder="E-Mail"/> :
                                <input onChange={onChangeEmail}
                                       className="form-control"
                                       value={email}
                                       type="email"
                                       placeholder={"E-Mail"}/>
                            }
                            <label className="form-label">E-Mail</label>
                        </div>
                    </div>
                    <div className="button-box">
                        <button type={"submit"} className="button-register">Register</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Register