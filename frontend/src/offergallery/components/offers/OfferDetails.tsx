import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Offer} from "../models/Offer";
import axios from "axios";
import "../../../styles/components/offers/OfferDetails.css"

export default function OfferDetails(){

    const navigate = useNavigate();
    const param = useParams()
    const [offer, setOffer] = useState<Offer>()

    const id: string | undefined = param.id

    useEffect(()=>{
    if(id) {
        getOfferById(id)
        }
    },[id])


    const handleChatButtonClick = () => {
        if (offer) {
            navigate(`/chat/${offer.author}`);
        }
    }

    function getOfferById(id: string){
        axios.get("/api/offers/"+id)
            .then(response=>response.data)
            .then(data=>{
                setOffer(data)
            })
            .catch(console.error)
    }

    return(
        <div>
            {!offer && <p className="loading-message">loading files...</p>}
            {offer &&
                <div className="offer-card">
                    <div className="offer-card_content">
                        <h2 className="offer-card_title">{offer.title}</h2>
                        <p className="offer-card_price">{offer.price}</p>
                        <p className="offer-card_description">{offer.description}</p>
                        <p className="offer-card_city">{offer.address.city}</p>
                        <p className="offer-card_zip">{offer.address.zip}</p>
                        <p className="offer-card_street">{offer.address.street}</p>
                    </div>
                    <div  className="offer-card_chat-button-box">
                    <button className="offer-card_chat-button" onClick={handleChatButtonClick}>Start Chat</button>
                    </div>
                </div>
            }
        </div>
    )
}