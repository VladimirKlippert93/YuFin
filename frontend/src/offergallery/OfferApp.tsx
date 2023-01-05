import OfferGallery from "./component/offers/OfferGallery";
import {useEffect, useState} from "react";
import {Offer} from "./models/Offer";
import axios from "axios";
import SearchBar from "./component/offers/SearchBar";

export default function OfferApp(){

    const [offers, setOffers] = useState<Offer[]>([])
    const [searchText, setSearchText] = useState<string>("")

    useEffect(()=>{
        getOffers()
    },[searchText])

    function getOffers(){
        axios.get("api/offers")
            .then((response)=>{
                setOffers(response.data)
            })
            .catch(e=>console.error(e))
    }

    const filteredSearch = offers.filter((offer)=> offer.title.toLowerCase().includes(searchText.toLowerCase()))

    function handleSearchText(searchText: string){
        setSearchText(searchText)
    }

    return (
        <div>
            <SearchBar handleSearchText={handleSearchText}/>
            <OfferGallery offerList={filteredSearch}/>
        </div>
    )
}