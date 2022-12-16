import OfferGallery from "./component/OfferGallery";
import {useEffect, useState} from "react";
import {Offer} from "./models/Offer";
import axios from "axios";

export default function OfferApp(){

    const [offer, setOffers] = useState<Offer[]>([])

    useEffect(()=>{
        getOffers()
    })

    function getOffers(){
        axios.get("api/offers")
            .then((response)=>{
                setOffers(response.data)
            })
            .catch(e=>console.error(e))
    }

    return (
        <div>
            <OfferGallery offerList={offer}/>
        </div>
    )
}