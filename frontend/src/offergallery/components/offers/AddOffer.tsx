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
        <div className= "form-container">

                <div className="form-content">
                    <form onSubmit={handleSubmit} className="form">
                        <div className="form-group">
                            <span className="form-label">Title:</span>
                            <input className="form-input" onChange={handleChange} type="text" placeholder="" value={offer.title} name={"title"}/>
                        </div>
                        <div className="form-group">
                            <span className="form-label">Price:</span>
                            <input className="form-input" onChange={handleChange} type="text" placeholder="" value={offer.price} name={"price"}/>
                        </div>
                        <div className="form-group">
                            <span className="form-label">Address:</span>
                            <input className="form-input" onChange={handleChangeAddress} type="text" placeholder="Street" value={address.street}
                                   name={"street"}/>
                            <input className="form-input" onChange={handleChangeAddress} type="text" placeholder="Streetnumber"
                                   value={address.streetNumber} name={"streetNumber"}/>
                            <input className="form-input" onChange={handleChangeAddress} type="text" placeholder="City" value={address.city}
                                   name={"city"}/>
                            <input className="form-input" onChange={handleChangeAddress} type="number" placeholder="Zip" value={address.zip}
                                   name={"zip"}/>
                            <input className="form-input" onChange={handleChangeAddress} type="text" placeholder="Country" value={address.country}
                                   name={"country"}/>
                        </div>
                        <div className="form-group">
                            <span className="form-label">Description:</span>
                            <input className="form-input" onChange={handleChange} type="text" placeholder="" value={offer.description}
                                   name={"description"}/>
                        </div>
                        <div className="form-group">
                            <button className="form-button" type={"submit"}>Add</button>
                        </div>
                    </form>
                </div>
        </div>
    )
}