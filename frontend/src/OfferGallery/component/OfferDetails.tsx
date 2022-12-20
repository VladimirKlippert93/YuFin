import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Offer} from "../models/Offer";
import axios from "axios";

export default function OfferDetails(){

    const param = useParams()
    const [offer, setOffer] = useState<Offer>()

    const id: string | undefined = param.id

    useEffect(()=>{
    if(id) {
        getOfferById(id)
    }
    },[id])

    function getOfferById(id: string){
        axios.get("/api/offers/details/"+id)
            .then(response=>response.data)
            .then(data=>{
                setOffer(data)
            })
            .catch(console.error)
    }

    return(
        <div>
            {!offer && <p>loading files...</p>}
            {offer &&
                <div>
                    <div>
                        <h2>{offer.title}</h2>
                        <hr/>
                        <p>{offer.price}</p>
                        <p>{offer.description}</p>
                        <p>{offer.address.city}</p>
                        <p>{offer.address.zip}</p>
                        <p>{offer.address.street}</p>
                    </div>
                </div>
            }
        </div>
    )

}