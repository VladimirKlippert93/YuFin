import {Address} from "cluster";

export type Offer = {
    id: string,
    title: string,
    price: string,
    address: Address[],
    description: string
}