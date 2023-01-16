import {Offer} from "../models/Offer";
import {ChangeEvent, FormEvent, useState} from "react";
import {Address} from "../models/Address";
import axios from "axios";
import {User} from "../models/User";

type OfferProps = {
    user: User
}

export default function AddOffer(props: OfferProps){

    const emptyFormPlaceholder: Offer = {
        "title": "",
        "price": "",
        "address":
            { "street": "", "streetNumber": "", "city": "", "zip": 0, "country": ""},
        "description": "",
        "author": ""
    }

    const emptyFormPlaceholderAddress: Address = {
        "street": "",
            "streetNumber": "",
            "city": "",
            "zip": 0,
            "country": ""
    }

    const [offer, setOffer] = useState<Offer>(emptyFormPlaceholder)
    const [address, setAddress] = useState<Address>(emptyFormPlaceholderAddress)


    const isAuthenticated: boolean = props.user.username !== 'anonymousUser' && props.user.username !== null && props.user.username !== undefined

    function handleSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        saveOffer(offer.title,offer.price,address,offer.description,props.user.username)
        setOffer(emptyFormPlaceholder)
        setAddress(emptyFormPlaceholderAddress)
    }
    function saveOffer(title: string,price: string, address: Address, description: string, author: string){
        return axios.post("api/offers/addoffer",{
            "title": title,
            "price": price,
            "address": address,
            "description": description,
            "author": author
        })
    }

    function handleChange(event: ChangeEvent<HTMLInputElement>){
        const changedInput = event.target.name

        setOffer(
            (prevState)=>({
                ...prevState,
                [changedInput]: event.target.value
            }))
    }

    function handleChangeAddress(event: ChangeEvent<HTMLInputElement>){
        const {name, value} = event.target

        setAddress(prevState => ({
            ...prevState,
            [name]: value
        }))
    }

    return (
        <div>{isAuthenticated ?
            <div>
                <form onSubmit={handleSubmit}>
                    <div>
                        <span>Title:</span>
                        <input onChange={handleChange} type="text" placeholder="" value={offer.title} name={"title"}/>
                    </div>
                    <div>
                        <span>Price:</span>
                        <input onChange={handleChange} type="text" placeholder="" value={offer.price} name={"price"}/>
                    </div>
                    <div>
                        <span>Address:</span>
                        <input onChange={handleChangeAddress} type="text" placeholder="Street" value={address.street}
                               name={"street"}/>
                        <input onChange={handleChangeAddress} type="text" placeholder="Streetnumber"
                               value={address.streetNumber} name={"streetNumber"}/>
                        <input onChange={handleChangeAddress} type="text" placeholder="City" value={address.city}
                               name={"city"}/>
                        <input onChange={handleChangeAddress} type="number" placeholder="Zip" value={address.zip}
                               name={"zip"}/>
                        <input onChange={handleChangeAddress} type="text" placeholder="Country" value={address.country}
                               name={"country"}/>
                    </div>
                    <div>
                        <span>Description:</span>
                        <input onChange={handleChange} type="text" placeholder="" value={offer.description}
                               name={"description"}/>
                    </div>
                    <div>
                        <button type={"submit"}>Add</button>
                    </div>
                </form>
            </div>: ""
        } </div>
    )
}