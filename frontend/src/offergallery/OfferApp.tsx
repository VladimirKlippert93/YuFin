import OfferGallery from "./components/offers/OfferGallery";
import {useEffect, useState} from "react";
import {Offer} from "./components/models/Offer";
import axios from "axios";
import SearchBar from "./components/offers/SearchBar";
import {User} from "./components/models/User";

type OfferAppProps={
    user: User
}
export default function OfferApp(props: OfferAppProps){

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
            <h1>{props.user.username}</h1>
            <SearchBar handleSearchText={handleSearchText}/>
            <OfferGallery offerList={filteredSearch}/>
        </div>
    )
}