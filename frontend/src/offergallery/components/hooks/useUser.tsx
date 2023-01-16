import {useEffect, useState} from "react";
import axios from "axios";
import {User} from "../models/User";

export default function useUser(){

    const emptyUser: User = {
        "username": "",
        "password": "",
        "email": "",
        "offerList": []
    }

    const [user, setUser] = useState<User>(emptyUser)

    useEffect(()=>{
        axios.get("/api/users/me")
            .then(result => result.data)
            .then(data => setUser(data))
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
                setUser(data)
                return data
            })
    }

    function logout() {
        return axios.post("/api/users/logout")
            .then((result) => result.data)
            .then((data) => {
                setUser(data)
                return data
            })
    }

    function register(username: string, password: string,email: string){
        axios.post("/api/users/register", {
            username: username,
            password: password,
            email: email
        }).catch(e => console.error(e))
    }
    return {user, login, logout, register}
}