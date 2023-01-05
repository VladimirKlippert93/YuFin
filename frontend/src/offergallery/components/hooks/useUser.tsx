import {useEffect, useState} from "react";
import axios from "axios";

export default function useUser(){

    const [username, setUsernamename] = useState<string>("")

    useEffect(()=>{
        axios.get("/api/users/me")
            .then(result => result.data)
            .then(setUsernamename)
    },[])

    function login(username: string, password: string){
        return axios.post("/api/users/login", undefined, {
            auth: {
                username,
                password
            },
        })
            .then((result)=> result.data)
            .then(data => {
                setUsernamename(data)
                return data
            })
    }

    function logout() {
        return axios.post("/api/users/logout")
            .then((result) => result.data)
            .then((data) => {
                setUsernamename(data)
                return data
            })
    }

    function register(username: string, email: string, password: string){
        axios.post("/api/users/register", {
            username: username,
            email: email,
            password: password
        }).catch(e => console.error(e))
    }
    return {username, login, logout, register}
}