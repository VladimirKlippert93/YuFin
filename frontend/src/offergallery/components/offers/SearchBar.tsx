import {ChangeEvent} from "react";

type SearchBarProps = {
    handleSearchText: (searchText: string)=>void
}

export default function SearchBar(props: SearchBarProps){
    function handleOnChangeSearchText(event: ChangeEvent<HTMLInputElement>){
        props.handleSearchText(event.target.value)
    }

    return (
        <div className="searchbar">
            <input className="searchbar_input" type="text" placeholder="Search for something..." onChange={handleOnChangeSearchText}/>
        </div>
    )
}