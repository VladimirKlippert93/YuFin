import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";

type RegisterProps = {
    register: (user: string, email: string, password: string)=>void
}

const Register = (props: RegisterProps) => {

    const [user, setUser] = useState<string>("")
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const navigation = useNavigate()

    function onChangeUser(event: ChangeEvent<HTMLInputElement>){
        setUser(event.target.value)
    }

    function onChangeEmail(event: ChangeEvent<HTMLInputElement>){
        setEmail(event.target.value)
    }

    function onChangePassword(event: ChangeEvent<HTMLInputElement>){
        setPassword(event.target.value)
    }

    function onRegisterSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        props.register(user,email,password)
        setUser("")
        setEmail("")
        setPassword("")
        navigation("/")
    }

    return(
        <div>
            <div>
                <h4>Register</h4>
            </div>
            <div>
                <form onSubmit={onRegisterSubmit}>
                    <div>
                        {user.length >= 1 ?
                            <input onChange={onChangeUser}
                                   value={user}
                                   type="text"
                                   placeholder="Name"/> :
                            <input type="test"
                                    onChange={onChangeUser}
                                    placeholder="Name"
                                    value={user}/>
                        }
                        <label>Name</label>
                    </div>
                    <div>
                        {email.includes('@') ?
                        <input onChange={onChangeEmail}
                                value={email}
                                type="email"
                                placeholder="E-Mail"/> :
                            <input onChange={onChangeEmail}
                                value={email}
                                type="email"
                                placeholder={"E-Mail"}/>
                            }
                            <label>E-Mail</label>
                    </div>
                    <div>
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
                        <label>Password</label>
                        <div>
                            <p>The Password needs at least 8 Characters.</p>
                        </div>
                    </div>
                    <div>
                        <button type={"submit"}>Register</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Register