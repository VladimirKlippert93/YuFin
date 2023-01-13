import {Offer} from "../models/Offer";
import {ChangeEvent, FormEvent, useState} from "react";
import {Address} from "../models/Address";
import axios from "axios";

type OfferProps = {
    username: string
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

    const [offer, setOffer] = useState<Offer>(emptyFormPlaceholder)

    const isAuthenticated: boolean = props.username !== 'anonymousUser' && props.username !== null && props.username !== undefined

    function handleSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        saveOffer(offer.title,offer.price,offer.address,offer.description,offer.author)
        setOffer(emptyFormPlaceholder)
    }
    function saveOffer(title: string,price: string, address: Address, description: string, author: string){
        return axios.post("api/addoffer",{
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
                        <input onChange={handleChange} type="text" placeholder="Street" value={offer.address.street}
                               name={"street"}/>
                        <input onChange={handleChange} type="text" placeholder="Streetnumber"
                               value={offer.address.streetNumber} name={"streetnumber"}/>
                        <input onChange={handleChange} type="text" placeholder="City" value={offer.address.city}
                               name={"city"}/>
                        <input onChange={handleChange} type="number" placeholder="Zip" value={offer.address.zip}
                               name={"zip"}/>
                        <input onChange={handleChange} type="text" placeholder="Country" value={offer.address.country}
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