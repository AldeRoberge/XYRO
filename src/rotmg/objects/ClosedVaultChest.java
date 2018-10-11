package rotmg.objects;

import alde.flash.utils.XML;
import rotmg.util.TextKey;

public class ClosedVaultChest extends SellableObject {


public  ClosedVaultChest(XML objectXML)  {
			super(objectXML);
		}

public String  soldObjectName()  {
			return TextKey.VAULT_CHEST;
		}

public String  soldObjectInternalName()  {
			return "Vault Chest";
		}

	}