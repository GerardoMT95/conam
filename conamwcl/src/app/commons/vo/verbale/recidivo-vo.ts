import { SelectVO } from "../select-vo";
import { SoggettoVO } from "./soggetto-vo";
import { verbaliRecidivoVO } from "./verbali-recidivo-vo";

export class RecidivoVo {
  public ruoloSoggetto: SelectVO;
  public soggettiVerbale: verbaliRecidivoVO;
  public soggetto: SoggettoVO;
}
