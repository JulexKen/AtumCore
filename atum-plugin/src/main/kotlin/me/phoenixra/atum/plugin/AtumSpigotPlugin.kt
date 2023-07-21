package me.phoenixra.atum.plugin

import me.phoenixra.atum.core.AtumAPI
import me.phoenixra.atum.core.AtumPlugin
import me.phoenixra.atum.core.command.AtumCommand
import me.phoenixra.atum.core.config.Config
import me.phoenixra.atum.craft.AtumAPICraft
import me.phoenixra.atum.plugin.commands.AtumCoreCommand
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


class AtumSpigotPlugin : AtumPlugin() {

    init {
        instance = this
    }

    override fun handleEnable() {
    }

    override fun handleDisable() {

    }

    override fun getAtumAPIVersion(): Int {
        return description.version.split(".")[1].toInt()
    }

    override fun loadPluginCommands(): MutableList<AtumCommand> {
        return mutableListOf(
            AtumCoreCommand(this)
        )
    }
    override fun loadAPI(): AtumAPI {
        return AtumAPICraft()
    }
    companion object{
        lateinit var instance : AtumSpigotPlugin
        private set
    }
}